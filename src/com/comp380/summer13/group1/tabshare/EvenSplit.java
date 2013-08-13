package com.comp380.summer13.group1.tabshare;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class EvenSplit extends Activity {
	EditText numOfPeopleInput;
	EditText billTotalInput;
	CheckBox tipBox;
	RadioButton percentFiveBtn;
	RadioButton percentTenBtn;
	RadioButton percentFifteenBtn;
	RadioButton roundUpBtn;
	TextView differenceTextView;
	TextView splitTotalTextView;
	TextView tipTextView;
	TextView totalTextView;
	double tipPercent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_even_split);
		tipBox = (CheckBox) findViewById(R.id.tip_checkbox);
		percentFiveBtn = (RadioButton) findViewById(R.id.percent5_btn);
		percentTenBtn = (RadioButton) findViewById(R.id.percent10_btn);
		percentFifteenBtn = (RadioButton) findViewById(R.id.percent15_btn);
		roundUpBtn = (RadioButton) findViewById(R.id.round_up_btn);
		numOfPeopleInput = (EditText) findViewById(R.id.number_payees_input);
		billTotalInput = (EditText) findViewById(R.id.total_input);
		differenceTextView = (TextView) findViewById(R.id.difference_text);
		splitTotalTextView = (TextView) findViewById(R.id.split_total_text);
		tipTextView = (TextView) findViewById(R.id.tip_text);
		totalTextView = (TextView) findViewById(R.id.total_text);
		tipPercent = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.even_split, menu);
		return true;
	}

	public void addTip(View view) {
		if (tipBox.isChecked()) {
			if (percentFiveBtn.getVisibility() != 0) {
				percentFiveBtn.setVisibility(0);
				percentTenBtn.setVisibility(0);
				percentFifteenBtn.setVisibility(0);
				roundUpBtn.setVisibility(0);
				tipPercent = .10;
			} else {
				switch (view.getId()) {
				case R.id.percent5_btn:
					percentTenBtn.setChecked(false);
					percentFifteenBtn.setChecked(false);
					roundUpBtn.setChecked(false);
					tipPercent = .05;
					break;
				case R.id.percent10_btn:
					percentFiveBtn.setChecked(false);
					percentFifteenBtn.setChecked(false);
					roundUpBtn.setChecked(false);
					tipPercent = .10;
					break;
				case R.id.percent15_btn:
					percentFiveBtn.setChecked(false);
					percentTenBtn.setChecked(false);
					roundUpBtn.setChecked(false);
					tipPercent = .15;
					break;
				case R.id.round_up_btn:
					percentFiveBtn.setChecked(false);
					percentTenBtn.setChecked(false);
					percentFifteenBtn.setChecked(false);
					break;
				}
			}
		} else {
			percentFiveBtn.setVisibility(-1);
			percentTenBtn.setVisibility(-1);
			percentFifteenBtn.setVisibility(-1);
			roundUpBtn.setVisibility(-1);
			tipPercent = 0;
		}
		splitBill(view);
	}

	public void splitBill(View view) {
		if (billTotalInput.getText().toString().equals("")
				|| numOfPeopleInput.getText().toString().equals("")) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					EvenSplit.this);

			// set title
			alertDialogBuilder.setTitle("Alert");
			alertDialogBuilder.setMessage("Enter Valid Numbers");
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		} else {
			// Calculate the totalSplit
			double billTotal = Double.parseDouble(billTotalInput.getText()
					.toString());
			double numOfPayees = Double.parseDouble(numOfPeopleInput.getText()
					.toString());
			double totalSplit = Math.round(100.0 * billTotal / numOfPayees) / 100.0;
			splitTotalTextView.setText(String.format("%.2f", totalSplit));

			// Calculate the difference
			double difference = billTotal - (totalSplit * numOfPayees);
			if (difference < 0) {
				differenceTextView.setText(String.format(
						"There is %.2f cents left to pay in the bill total",
						Math.abs(difference)));
				differenceTextView.setVisibility(0);
			} else if (difference > 0) {
				differenceTextView.setText(String.format(
						"There will %.2f cents paid over the bill total",
						Math.abs(difference)));
				differenceTextView.setVisibility(0);
			} else {
				differenceTextView.setVisibility(0);
			}

			// Calculate the tip
			double tipTotal = 0;
			if (roundUpBtn.isChecked()) {
				if (totalSplit % 1.0 != 0) {
					tipTotal = 1.00 - totalSplit % 1.0;
				} else {
					tipTotal = 0.00;
				}
			} else {
				tipTotal = Math.round(100.0 * totalSplit * tipPercent) / 100.0;
			}
			tipTextView.setText(String.format("%.2f", tipTotal));

			// calculate the total
			double total = totalSplit + tipTotal;
			totalTextView.setText(String.format("%.2f", total));
		}
	}
}
