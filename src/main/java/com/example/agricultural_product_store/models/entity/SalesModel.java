package com.example.agricultural_product_store.models.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.sql.Date;

@Data
@NamedNativeQueries(
        {
                @NamedNativeQuery(
                        name = "getDailySales",
                        query = "SELECT datefield as date, IFNULL(SUM(quantity), 0) as total FROM calendar LEFT JOIN order_item ON date_format(datefield, '%Y-%m-%d') = date_format(create_time, '%Y-%m-%d') AND product_id = :productId WHERE datefield BETWEEN '2023-06-01' AND curdate() GROUP BY datefield",
                        resultSetMapping = "SalesMapping"
                ),
                @NamedNativeQuery(
                        name = "getMonthlySales",
                        query = "SELECT date_format(datefield, '%Y-%m'), IFNULL(SUM(quantity), 0) as total FROM calendar LEFT JOIN order_item ON date_format(datefield, '%Y-%m-%d') = date_format(create_time, '%Y-%m-%d') AND product_id = :productId WHERE datefield BETWEEN '2023-06-01' AND curdate() GROUP BY date_format(datefield, '%Y-%m')",
                        resultSetMapping = "SalesMapping"
                )
        })
@SqlResultSetMapping(
        name = "SalesMapping",
        classes = {
                @javax.persistence.ConstructorResult(
                        targetClass = SalesModel.class,
                        columns = {
                                @javax.persistence.ColumnResult(name = "date", type = Date.class),
                                @javax.persistence.ColumnResult(name = "total", type = Double.class)
                        }
                )
        }
)
public class SalesModel {
    private Date date;
    private double total;
}
