package com.example.parking.services;

import com.example.parking.dao.DriverDAO;
import com.example.parking.dao.ParkingLotDAO;
import com.example.parking.dao.TopLotsDAO;
import com.example.parking.dao.TopUsersDAO;
import com.example.parking.dto.DriverDTO;
import com.example.parking.dto.ParkingLotDTO;
import com.example.parking.dto.TopLotsDTO;
import com.example.parking.dto.TopUsersDTO;
import com.example.parking.entities.TopLotsReport;
import com.example.parking.entities.TopUsersReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    TopUsersDAO topUsersDAO;
    TopLotsDAO topLotsDAO;
    DriverDAO driverDAO;
    ParkingLotDAO parkingLotDAO;

    public AdminService(TopUsersDAO topUsersDAO, DriverDAO driverDAO, TopLotsDAO topLotsDAO, ParkingLotDAO parkingLotDAO) {
        this.topUsersDAO = topUsersDAO;
        this.driverDAO = driverDAO;
        this.topLotsDAO = topLotsDAO;
        this.parkingLotDAO = parkingLotDAO;
    }

    public ResponseEntity<FileSystemResource> returnTopUsers() throws FileNotFoundException, JRException {
        List<TopUsersDTO> topUsers = topUsersDAO.getTopDriver();
        List<TopUsersReport> top = new ArrayList<>();
        for (TopUsersDTO user : topUsers) {
            DriverDTO driver = driverDAO.getDriverById(user.getDriver_id());
            top.add(new TopUsersReport(driver.getFull_name(), user.getNumber_of_resvs(), driver.getLicense_plate()));
        }

        String path = "backend\\src\\main\\resources\\topUsers.html";
        Map<String, Object> params = new HashMap<>();
        params.put("Report Owner", "System Admin");
        File file = ResourceUtils.getFile("classpath:topUsers.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(top);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, path);
        return sendHttpResponse(path);
    }

    public ResponseEntity<FileSystemResource> returnTopLots() throws JRException, FileNotFoundException {
        List<TopLotsDTO> topLots = topLotsDAO.getTopLots();
        List<TopLotsReport> top = new ArrayList<>();
        for (TopLotsDTO lot : topLots) {
            ParkingLotDTO parkingLot = parkingLotDAO.getLotById(lot.getLot_id());
            top.add(new TopLotsReport(parkingLot.getName(), lot.getRevenue(), parkingLot.getCapacity()));
        }

        String path = "backend\\src\\main\\resources\\topLots.html";
        Map<String, Object> params = new HashMap<>();
        params.put("Report Owner", "System Admin");
        File file = ResourceUtils.getFile("classpath:topLots.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(top);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, path);
        return sendHttpResponse(path);
    }

    ResponseEntity<FileSystemResource> sendHttpResponse(String path) {
        File sentFile = new File(path);
        FileSystemResource fileResource = new FileSystemResource(sentFile);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html");
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
    }
}
