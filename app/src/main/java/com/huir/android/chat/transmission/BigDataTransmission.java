package com.huir.android.chat.transmission;

import com.huir.android.entity.Image;

import java.util.ArrayList;

/**
 * activity之间的大数据量的传输
 *
 */
public class BigDataTransmission {
    private static  BigDataTransmission bigDataTransmission;


    public BigDataTransmission() {
    }

    public static BigDataTransmission getInstance(){
       if(bigDataTransmission == null){
            synchronized (BigDataTransmission.class){
                if(bigDataTransmission == null){
                    bigDataTransmission = new BigDataTransmission();
                }
            }
        }
        return  bigDataTransmission ;
    }



    /**SelectorViewActivity 与  PreviewPicturesActivity  之间的图片数组传输**/
    private ArrayList<Image> images;

    public ArrayList<Image> getList(){
        return  images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    /**
     * /* private GetSelectorList selector;
     public interface GetSelectorList{
     void setList(ArrayList<Image> images);
     }

     public void setCallBackAndGetList(GetSelectorList selector){
     if(selector != null){
     this.selector = selector;
     }
     }

     public void setCallBackAndSetList(ArrayList<Image> images){
     if( selector != null){

     Log.e("      CallBackManager  ", "setCallBackAndSetList: " + images.size() );
     selector.setList(images);
     }else{
     Log.e("CallBackManager", "setCallBackAndSetList:   not in "  );
     }
     }*/



}
