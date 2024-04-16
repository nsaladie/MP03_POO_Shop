package model;

import main.Payable;

public class Client extends Person implements Payable {
	final private int MEMBER_ID = 456;
	final private double BALANCE = 50.00;
	private int member_id = MEMBER_ID;
	private Amount amount = new Amount(BALANCE);

	public Client(String client) {
		super(client);
	}

	public Amount getAmount() {
		return amount;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getMEMBER_ID() {
		return MEMBER_ID;
	}

	public double getBALANCE() {
		return BALANCE;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public boolean pay(Amount amount) {
		double total = BALANCE - amount.getValue();

		if (total >= 0) {
			return true;
		}
		return false;
	}

}
