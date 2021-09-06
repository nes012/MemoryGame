package nesty.anzhy.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<ImageView> imageViewList = new ArrayList<>();
    //cards from 1 to 12. we will shuffle them in our game
    List<Integer>cardsList = new ArrayList<>();
    //map where key is number of card 1-12 and value is img resource
    Map<Integer, Integer> map;
    int firstCardNumber, secondCardNumber, firstTagNum, secondTagNum;
    int cardNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        //add num of cards to our list
        for (int i = 0; i < 12; i++) {
            cardsList.add(i+1);
        }
        //load to map. key - number of card value id image resource
        map = setMap();
        //shuffle the images
        Collections.shuffle(cardsList);
        //our listener to image buttons
        for (ImageView v : imageViewList) {
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        for (ImageView imgCard : imageViewList) {
            if (view.getId() == imgCard.getId()) {
                //change view on click
                changeCardImg(imgCard, Integer.parseInt((String) view.getTag()));
            }
        }
    }

    private void changeCardImg(ImageView iv, int cardTag) {
        // set the correct image to the imageview
        for(Map.Entry<Integer, Integer>m:map.entrySet()){
            if(cardsList.get(cardTag).equals(m.getKey())){
                iv.setImageResource(m.getValue());
            }
        }
        //save information to clickedFirst
        if (cardNumber == 1) {
            firstCardNumber = cardsList.get(cardTag);
            if (firstCardNumber > 6) {
                firstCardNumber -= 6;
            }
            cardNumber = 2;
            firstTagNum = cardTag;
            iv.setEnabled(false);
        } else if (cardNumber == 2) {
            secondCardNumber = cardsList.get(cardTag);
            if (secondCardNumber > 6) {
                secondCardNumber -= 6;
            }
            cardNumber = 1;
            secondTagNum = cardTag;

            for (ImageView v : imageViewList) {
                v.setEnabled(false);
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkIfTwoCardsEquals();
                }
            }, 1000);
        }
    }

    private void checkIfTwoCardsEquals() {
        //if images are equal remove them and add points
        if (firstCardNumber == secondCardNumber) {
            for (int i = 0; i < 12; i++) {
                if (firstTagNum == i || secondTagNum == i) {
                    imageViewList.get(i).setVisibility(View.INVISIBLE);
                }
            }
        } else {
            for (ImageView v : imageViewList) {
                v.setImageResource(R.drawable.square);
            }
        }
        for (ImageView v : imageViewList) {
            v.setEnabled(true);
        }
        checkEnd();
    }

    private void checkEnd() {
        for(ImageView v: imageViewList) {
            if (!(v.getVisibility() == View.INVISIBLE)) {
                return;
            }
        }
        Toast.makeText(getApplicationContext(), "GAME END", Toast.LENGTH_LONG).show();
    }

    private void findView() {
        imageViewList.add(findViewById(R.id.img11));
        imageViewList.add(findViewById(R.id.img12));
        imageViewList.add(findViewById(R.id.img13));
        imageViewList.add(findViewById(R.id.img21));
        imageViewList.add(findViewById(R.id.img22));
        imageViewList.add(findViewById(R.id.img23));
        imageViewList.add(findViewById(R.id.img31));
        imageViewList.add(findViewById(R.id.img32));
        imageViewList.add(findViewById(R.id.img33));
        imageViewList.add(findViewById(R.id.img41));
        imageViewList.add(findViewById(R.id.img42));
        imageViewList.add(findViewById(R.id.img43));
    }

    private Map<Integer, Integer> setMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, R.drawable.img1);
        map.put(2, R.drawable.img2);
        map.put(3, R.drawable.img3);
        map.put(4, R.drawable.img4);
        map.put(5, R.drawable.img5);
        map.put(6, R.drawable.img6);
        map.put(7, R.drawable.img7);
        map.put(8, R.drawable.img8);
        map.put(9, R.drawable.img9);
        map.put(10, R.drawable.img10);
        map.put(11, R.drawable.img11);
        map.put(12, R.drawable.img12);
        return map;
    }
}