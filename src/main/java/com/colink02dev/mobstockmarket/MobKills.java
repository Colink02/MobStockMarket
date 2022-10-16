package com.colink02dev.mobstockmarket;

public class MobKills {
    private int killCount = 0;
    private double payment;

    public MobKills(double payment) {
        this.payment = payment;
    }

    public int getKillCount() {
        return killCount;
    }

    public void addKill() {
        MobStockMarket.mobCount++;
        killCount++;
    }

    public void resetKills() {
        killCount = 0;
    }

    public double getPayment() {
        return this.payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }







}
