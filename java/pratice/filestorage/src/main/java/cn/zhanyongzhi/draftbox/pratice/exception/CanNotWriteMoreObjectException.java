package cn.zhanyongzhi.draftbox.pratice.exception;

public class CanNotWriteMoreObjectException extends FileStorageException {
    public CanNotWriteMoreObjectException(){
        super("can not write more object");
    }
}
