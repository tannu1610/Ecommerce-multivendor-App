package NayaBazzar.service;

import NayaBazzar.model.Seller;
import NayaBazzar.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport( SellerReport sellerReport);

}
