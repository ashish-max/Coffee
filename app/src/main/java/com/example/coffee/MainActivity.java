package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity
{
    int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increment(View view)
    {
        quantity=quantity+1;
        if(quantity>=1 && quantity<=20)
        {
            displayQuantity(quantity);
        }
        else{
            Toast.makeText(this,"Limit extended",Toast.LENGTH_SHORT).show();
            quantity=20;
        }
    }
    public void decrement(View view)
    {
        quantity=quantity-1;
        if(quantity>=1 && quantity<=20)
        {
            displayQuantity(quantity);
        }
        else{
            Toast.makeText(this,"Minimum order is 1",Toast.LENGTH_SHORT).show();
            quantity=1;
        }

    }
    public void submitOrder(View view)
    {
        EditText text=(EditText)findViewById(R.id.name_editable);
        String  name=text.getText().toString();
        CheckBox whippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(name,price,hasWhippedCream,hasChocolate);
        if(name.equals("")==false) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "COFFEE ORDER FOR:" + name);
            sendIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            sendIntent.setType("text/plain");
            if (sendIntent.resolveActivity(getPackageManager()) != null)//checking if there is an app or not to handle it.
            {
                startActivity(sendIntent);
            }
            }
            else
                Toast.makeText(this,"Enter name and details.",Toast.LENGTH_SHORT).show();
            displayMessage(priceMessage);
    }
    private  int calculatePrice(boolean addWhippedCream,boolean addChocolate)
    {
        int baseprice=10;
        if(addWhippedCream)
        {
            baseprice+=1;
        }
        if(addChocolate)
        {
            baseprice+=2;
        }

        return quantity*baseprice;
    }
    public void displayQuantity(int number)
    {
        TextView quantityTextView= findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }

    private String createOrderSummary(String Name,int price,boolean addWhippedCream,boolean addChocolate)
    {
        if(Name.equals("")==false) { //codition added again to help user.
            String priceMessage = "Name: " + Name;
            if(addWhippedCream)
                priceMessage += "\nAdd whipped cream? : Yes! ";
            else
                priceMessage += "\nAdd whipped cream? : No! ";
            if(addChocolate)
                priceMessage += "\nAdd chocolate? : Yes! ";
            else
                priceMessage += "\nAdd chocolate? : No! ";
            priceMessage += "\nQuantity:" + quantity;
            priceMessage += "\nTotal price: Rs - " + price;
            priceMessage += "\nTHANK YOU FOR ORDERING...HAVE A GOOD DAY!";
            return priceMessage;
        }

        else{
            String priceMessage="Please enter your name and other order details.";
            return priceMessage;
        }
    }
    public void displayMessage(String priceMessage)
    {
        TextView summaryTextView= findViewById(R.id.price_text_view);
        summaryTextView.setText(priceMessage);
    }
}