package io.asia.store.repository;

import io.asia.store.domain.dao.UserOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    Page<UserOrder> findByOrderNumber(String orderNumber, Pageable pageable);
    @Query(value = "SELECT us.order_number as orderNumber, us.created_date as createdDate, SUM(p.price*us.quantity) as totalPrice " +
            "FROM user_order us JOIN product p ON us.product_id=p.id " +
            "where us.user_id=?1 " +
            "group by order_number", nativeQuery = true)
    List<Order> findOrders(Long userId);

    interface Order {
        String getOrderNumber();
        void setOrderNumber(String orderNumber);
        String getCreatedDate();
        void setCreatedDate(String createdDate);
        Double getTotalPrice();
        void setTotalPrice(Double totalPrice);
    }
}
