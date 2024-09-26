package com.example.prm392_miniproject_racenimal;

import java.util.ArrayList;

public class AccountManager {
    private static AccountManager instance;
    private ArrayList<Account> accountList;

    private AccountManager() {
        accountList = new ArrayList<>();
        initializeAccounts(); // Prepopulate accounts if needed
    }

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
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

    public void addAccount(Account account) {
        accountList.add(account);
    }
}

