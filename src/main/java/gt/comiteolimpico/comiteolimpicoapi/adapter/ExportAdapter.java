package gt.comiteolimpico.comiteolimpicoapi.adapter;

import java.io.OutputStream;
import java.util.List;

public interface ExportAdapter<T> {
    void export(List<T> data, OutputStream outputStream) throws Exception;
    List<T> importData(java.io.InputStream inputStream) throws Exception;
}