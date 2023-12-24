package bo.custom.impl;

import bo.custom.ItemBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.util.DaoType;
import dto.ItemDto;
import entity.Item;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    private ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);

        @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.save(new Item(
                dto.getCode(),
                dto.getDesc(),
                dto.getUnitPrice(),
                dto.getQty()
        ));
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.update(new Item(
                dto.getCode(),
                dto.getDesc(),
                dto.getUnitPrice(),
                dto.getQty()
        ));
    }
    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDao.delete(id);
    }

    @Override
    public List<ItemDto> allItem() throws SQLException, ClassNotFoundException {
        List<Item> entityList = itemDao.getAll();
        List<ItemDto> list = new ArrayList<>();
        for (Item customer:entityList) {
            list.add(new ItemDto(
                    customer.getCode(),
                    customer.getDescription(),
                    customer.getUnitPrice(),
                    customer.getQtyOnHand()
            ));
        }
        return list;
    }


}


