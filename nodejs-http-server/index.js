const http = require('http');
const sqlite3 = require('sqlite3').verbose();

let db = new sqlite3.Database('./db/info.db', (err) => {
    if (err) {
        console.error(err.message);
    }

    console.log('Connected to the chinook database.');
});

db.serialize(() => {
    // Queries scheduled here will be serialized.
    db.run('CREATE TABLE IF NOT EXISTS info(id INT PRIMARY KEY NOT NULL, status INT)');
});

String.prototype.startWith = function (str) {
    const reg = new RegExp("^" + str);
    return reg.test(this);
};

String.prototype.endWith = function (str) {
    const reg = new RegExp(str + "$");
    return reg.test(this);
};

http.createServer(function (req, res) {
    res.setHeader("Access-Control-Allow-Origin", "*");

    const path = req.url;
    if (path.startWith("/getStatus")){
        const id = path.split("/")[2];
        const sql = "select status from info where id = ?";
        db.get(sql, id, (err, row) => {
            console.log(row);
            console.log(err);

            const result = {
                status: 0
            };

            if(row){
                result.status = row.status;
            }

            res.end(JSON.stringify(result));
        });

        return;
    }

    if(path.startsWith("/setStatus")){
        const id = path.split("/")[2];
        const status = path.split("/")[3];
        const sql = "replace into info (id, status) values (?, ?)";
        const result = {success: 0};
        db.run(sql, [id, status], (err) => {
            if(!err)
                result.success = 1;

            res.end(JSON.stringify(result));
        });
    }
}).listen(8083);