package dao.custom;
import java.util.List;
import dao.CrudDao;
import entity.Item;
import dto.ItemDto;
import java.sql.SQLException;

public interface ItemDao extends CrudDao<Item> {
//    boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;
//    boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException;
//    boolean deleteItem(ItemDto id) throws SQLException, ClassNotFoundException;
    List<ItemDto> allItems() throws SQLException, ClassNotFoundException;
    ItemDto searchItem(String id);
}
