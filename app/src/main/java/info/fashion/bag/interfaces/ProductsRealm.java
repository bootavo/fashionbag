package info.fashion.bag.interfaces;

import java.util.List;

import info.fashion.bag.models.JsonProducts;
import info.fashion.bag.models.Product;
import info.fashion.bag.models.Products;

/**
 * Created by gtufinof on 3/17/18.
 */

public class ProductsRealm {

    /*
    private Realm realm;

    public ProductsRealm(Realm realm){
        this.realm = realm;
    }

    public List<Products> getBagsOffersRealm(){
        RealmResults<Products> results = realm.where(Products.class).findAll();
        List<Products> listProducts = realm.copyFromRealm(results);
        return listProducts;
    }

    public List<Products> getJewelsOffersRealm(){
        RealmResults<Products> results = realm.where(Products.class).findAll();
        List<Products> listProducts = realm.copyFromRealm(results);
        return listProducts;
    }

    public List<Products> getGeneralProductsRealm(){
        RealmResults<Products> results = realm.where(Products.class).findAll();
        List<Products> listProducts = realm.copyFromRealm(results);
        return listProducts;
    }

    public void setListProducts(List<Products> products){
        realm.beginTransaction();
        realm.copyToRealm(products);
        realm.commitTransaction();
    }
    */

}
