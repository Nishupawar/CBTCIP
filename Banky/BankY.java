import java.io.*;
import java.util.*;


class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String accountHolderName;
    private double balance;

  
    public BankAccount(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }


    public void deposit(double amount) {
        balance += amount;
    }

    
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }

   
    public void transfer(BankAccount receiver, double amount) {
        if (balance >= amount) {
            balance -= amount;
            receiver.deposit(amount);
            System.out.println(amount + " transferred successfully to " + receiver.getAccountHolderName());
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

 
    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: $" + balance);
    }
}


public class BankY {
    private static final String DATABASE_FILE = "bank_accounts.dat";
    private static List<BankAccount> bankAccounts = new ArrayList<>();

    public static void createAccount(String accountNumber, String accountHolderName, double initialBalance) {
        BankAccount account = new BankAccount(accountNumber, accountHolderName, initialBalance);
        bankAccounts.add(account);
        saveBankAccounts();
    }

    
    public static void deposit(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            saveBankAccounts();
        } else {
            System.out.println("Account not found.");
        }
    }

  
    public static void withdraw(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            saveBankAccounts();
        } else {
            System.out.println("Account not found.");
        }
    }

    
    public static void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        BankAccount fromAccount = findAccount(fromAccountNumber);
        BankAccount toAccount = findAccount(toAccountNumber);

        if (fromAccount != null && toAccount != null) {
            fromAccount.transfer(toAccount, amount);
            saveBankAccounts();
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

   
    private static BankAccount findAccount(String accountNumber) {
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }


    private static void saveBankAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATABASE_FILE))) {
            oos.writeObject(bankAccounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    @SuppressWarnings("unchecked")
    private static void loadBankAccounts() {
        File file = new File(DATABASE_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                bankAccounts = (List<BankAccount>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
       
        loadBankAccounts();

     
        createAccount("1001", "Nishu", 500.0);
        createAccount("1002", "Vikram", 1000.0);

        deposit("1001", 2000.0);
        withdraw("1002", 350.0);

        transfer("1002", "1001", 150.0);

        
        for (BankAccount account : bankAccounts) {
            account.displayAccountDetails();
            System.out.println();
        }
    }
}
