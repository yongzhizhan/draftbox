package cn.zhanyongzhi.draftbox.pratice.exception;

public class FileRemainSizeLessThanZeroException extends FileStorageException {
    public FileRemainSizeLessThanZeroException(){
        super("file remain size less than zero");
    }
}
