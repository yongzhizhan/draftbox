package cn.zhanyongzhi.draftbox.pratice;

import cn.zhanyongzhi.draftbox.pratice.utils.SnowflakeIdWorker;

class KeyGenerate {
    private static final KeyGenerate instance = new KeyGenerate();
    private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    private KeyGenerate(){

    }

    public static void setIdWorkerProperty(long workId, long datacenterId){
        idWorker = new SnowflakeIdWorker(workId, datacenterId);
    }

    public static KeyGenerate getInstance(){
        return instance;
    }

    public long nextId(){
        return idWorker.nextId();
    }

}
