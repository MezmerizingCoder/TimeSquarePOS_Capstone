/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.test;

import pos_timesquare.controller.StoreInfoService;
import pos_timesquare.model.StoreInfo;

/**
 *
 * @author Acer
 */
public class TestStoreInfo {
    public static void main(String[] args) {
        StoreInfoService ss = new StoreInfoService();
        StoreInfo si = ss.getStoreInfoDetails();
        
        System.out.println(si.getId());
        System.out.println(si.getName());
        si.setContactNum("9999999");
        
        ss.updateStoreInfo(si.getId(), si);
    }
}
