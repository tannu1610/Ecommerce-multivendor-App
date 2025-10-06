package NayaBazzar.service;

import NayaBazzar.model.Home;
import NayaBazzar.model.HomeCategory;

import java.util.List;

public interface HomeService {

    Home creatHomePageData(List<HomeCategory> categories);

}
