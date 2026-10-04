package dao;

import database.DatabaseConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDAOImpl implements ProductDAO {


    @Override
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM webshop.products";

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                );

                products.add(p);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Could not load products from database.",
                    e
            );
        }

        return products;
    }





    @Override
    public Product getProductById(int id){


        Product product=null;


        String sql =
                "SELECT * FROM webshop.products WHERE id=?";


        try(Connection con=DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){


            ps.setInt(1,id);

            ResultSet rs=ps.executeQuery();


            if(rs.next()){

                product=new Product(

                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")

                );

            }


        }catch(Exception e){
            e.printStackTrace();
        }


        return product;
    }


    @Override
    public void addProduct(Product product){


        String sql =
                "INSERT INTO webshop.products(name,price,stock,category_id) VALUES(?,?,?,?)";


        try(Connection con=DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){


            ps.setString(1,product.getName());
            ps.setDouble(2,product.getPrice());
            ps.setInt(3,product.getStock());
            ps.setInt(4,product.getCategoryId());


            ps.executeUpdate();


        }catch(Exception e){
            e.printStackTrace();
        }

    }





    @Override
    public void updateProduct(Product product){


        String sql =
                "UPDATE webshop.products SET name=?,price=?,stock=?,category_id=? WHERE id=?";


        try(Connection con=DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){


            ps.setString(1,product.getName());
            ps.setDouble(2,product.getPrice());
            ps.setInt(3,product.getStock());
            ps.setInt(4,product.getCategoryId());
            ps.setInt(5,product.getId());


            ps.executeUpdate();


        }catch(Exception e){
            e.printStackTrace();
        }

    }





    @Override
    public void deleteProduct(int id){


        String sql =
                "DELETE FROM webshop.products WHERE id=?";


        try(Connection con=DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){


            ps.setInt(1,id);

            ps.executeUpdate();


        }catch(Exception e){
            e.printStackTrace();
        }

    }


}