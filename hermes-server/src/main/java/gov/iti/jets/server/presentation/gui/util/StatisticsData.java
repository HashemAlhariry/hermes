package gov.iti.jets.server.presentation.gui.util;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum StatisticsData {

    INSTANCE;


    public SimpleIntegerProperty onlineUsers = new SimpleIntegerProperty(0);
    public SimpleIntegerProperty offlineUsers = new SimpleIntegerProperty(0);

    public SimpleIntegerProperty maleUsers = new SimpleIntegerProperty(0);
    public SimpleIntegerProperty femaleUsers = new SimpleIntegerProperty(0);

    public ObservableList<PieChart.Data> pieChartDataForGender  = FXCollections.observableArrayList(
            new PieChart.Data("Males", 0),
            new PieChart.Data("Females", 0));

    public ObservableList<PieChart.Data> pieChartDataForOnlineOffline = FXCollections.observableArrayList(
            new PieChart.Data("Online",  0),
            new PieChart.Data("Offline", 0));


    private  List<PieChart.Data> countriesUser = new ArrayList<>();
    private  Map<String,Integer> countryMapValue = new HashMap<>();
    public ObservableList<PieChart.Data> pieChartDataForCountry = FXCollections.observableArrayList(countriesUser);


    public void setOnlineUsers(int onlineUsers) {
        this.onlineUsers.set(onlineUsers);
        pieChartDataForOnlineOffline.get(0).setPieValue(onlineUsers);
    }
    public void setOfflineUsers(int offlineUsers){
       this.offlineUsers.set(offlineUsers);
       pieChartDataForOnlineOffline.get(1).setPieValue(offlineUsers);
   }
    public void setMaleUsers(int maleUsers){
       this.maleUsers.set(maleUsers);
       pieChartDataForGender.get(0).setPieValue(maleUsers);
   }
    public void setFemaleUsers(int femaleUsers){
        this.femaleUsers.set(femaleUsers);
        pieChartDataForGender.get(1).setPieValue(femaleUsers);

    }


    public void setPieChartDataForCountry(Map<String,Integer> countryMap){
        countryMapValue=countryMap;
        for (Map.Entry<String, Integer> entry : countryMap.entrySet()) {
           countriesUser.add( new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChartDataForCountry.setAll(countriesUser);
    }
    public void updatePieChartDataForCountry(String countryName){
            if(countryMapValue.containsKey(countryName)){
                countryMapValue.replace(countryName,countryMapValue.get(countryName)+1);
                for (int i =0;i<countriesUser.size();i++){
                    if(countriesUser.get(i).getName().equals(countryName)){
                        countriesUser.get(i).setPieValue( countriesUser.get(i).getPieValue()+1);
                    }
                }

            }else{
                countriesUser.add(new PieChart.Data(countryName ,1));
            }



        Platform.runLater(()->{
            pieChartDataForCountry.clear();
            pieChartDataForCountry.setAll(countriesUser);
                });





    }



}
