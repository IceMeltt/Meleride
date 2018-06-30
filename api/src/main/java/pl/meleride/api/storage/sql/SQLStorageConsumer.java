package pl.meleride.api.storage.sql;

import java.sql.PreparedStatement;
import pl.meleride.api.storage.StorageConsumer;

public interface SQLStorageConsumer extends StorageConsumer<PreparedStatement> {

}
