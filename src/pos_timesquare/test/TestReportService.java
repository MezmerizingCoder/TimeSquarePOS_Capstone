/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos_timesquare.test;

import pos_timesquare.controller.ReportService;
import pos_timesquare.model.Reports;

/**
 *
 * @author Acer
 */
public class TestReportService {
    public static void main(String[] args) {
        ReportService rs = new ReportService();
        
//        Reports report = new Reports();
//        report.setType("email");
//        report.setDate("2022-11-23 12:22:16.711");
//        
//        rs.addReport(report);
        
        rs.getAllReports().forEach(e -> {
            System.out.println(e.getId());
        });
        
        String s="1,2022";
        String requiredString = s.substring(s.indexOf(",") + 1, s.length());
        String requiredString2 = s.substring(0, s.indexOf(","));
        
        System.out.println(requiredString);
        System.out.println(requiredString2);
        
    }
}
