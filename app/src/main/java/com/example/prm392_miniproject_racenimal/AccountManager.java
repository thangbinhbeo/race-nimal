package com.example.prm392_miniproject_racenimal;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class AccountManager {
    private static AccountManager instance;
    private ArrayList<Account> accountList;
    private Context context;

    private AccountManager(Context context) {
        this.context = context;
        accountList = new ArrayList<>();
        initializeAccounts(); // Prepopulate accounts if needed
        loadAccounts(); // Load existing accounts from SharedPreferences
    }

    public static AccountManager getInstance(Context context) {
        if (instance == null) {
            instance = new AccountManager(context);
        }
        return instance;
    }

    private void initializeAccounts() {
        // Add some dummy accounts for testing
        accountList.add(new Account("1", "1", 100));
        accountList.add(new Account("user2", "password2", 100));
        accountList.add(new Account("user3", "password3", 100));
    }

    public ArrayList<Account> getAccountList() {
        return accountList;
    }

    public Account getAccount(String username, String password) {
        for (Account account : accountList) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public Account getAccount(Account user) {
        for (Account account : accountList) {
            if (account.getUsername().equals(user.getUsername()) && account.getPassword().equals(user.getPassword())) {
                return account;
            }
        }
        return null;
    }

    public void addMoney(Account account, double money) {
        getInstance(context).getAccount(account).setBudget(account.getBudget() + money);
        saveAccounts(); // Update saved data after adding money
    }

    public void addAccount(Account account) {
        accountList.add(account);
        saveAccounts(); // Update saved data after adding a new account
    }

    public void saveAccounts() {
        SharedPreferences prefs = context.getSharedPreferences("account_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (Account account : accountList) {
            editor.putFloat(account.getUsername() + "_budget", (float) account.getBudget());
        }
        editor.apply();
    }

    private void loadAccounts() {
        SharedPreferences prefs = context.getSharedPreferences("account_data", Context.MODE_PRIVATE);

        for (Account account : accountList) {
            float budget = prefs.getFloat(account.getUsername() + "_budget", 100); // Default budget
            account.setBudget(budget);
        }
    }
}