# 文档

启动分为两步，数据库和服务

数据库启动：

    docker run -d --name spider_db  \
        -e MYSQL_ROOT_PASSWORD=spider_db_pwd \
        -e MYSQL_DATABASE=yueyuso \
        -v /opt/yueyuso/mysql/data:/var/lib/mysql \
        123.56.168.19:5000/yueyuso-mainserverdb
        
服务启动：
    
    docker run -d --name spider --link spider_db -e DB_USER=root \
        -e DB_PWD=spider_db_pwd \
        -e DB_CONN=jdbc\:mysql\:\/\/spider_db\:3306\/yueyuso?useUnicode=true\\\&characterEncoding=utf-8 \
        -e archive=spider-1.0-SNAPSHOT \
        -v /opt/yueyuso/spider/storage:/opt/spider-1.0-SNAPSHOT/storage \
        -p 8094:8080 \
        123.56.168.19:5000/yueyuso-spider
