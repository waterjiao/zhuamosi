package com.zhua.mosi.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer currentPage;
    private Integer totalPage;
    private ArrayList<Integer> paginationList = new ArrayList<>();


    public void setPagination(Integer totalPage, Integer currentPage, Integer size) {
        this.totalPage = totalPage;
        this.currentPage = currentPage;

        if(currentPage-3>0){
            paginationList.add(currentPage-3);
            paginationList.add(currentPage-2);
            paginationList.add(currentPage-1);
            paginationList.add(currentPage);
        }else {
            for(int i=1;i<=currentPage;i++){
                paginationList.add(i);
            }
        }
        if(currentPage+3<=totalPage){
            paginationList.add(currentPage+1);
            paginationList.add(currentPage+2);
            paginationList.add(currentPage+3);
        }else {
            for(int i=currentPage+1;i<=totalPage;i++){
                paginationList.add(i);
            }
        }

        if(paginationList.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        if (paginationList.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
        if (currentPage==1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        if (currentPage==totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        //   << < 1 2 3 4 5 6 7 > >>
    }
}
