package in.webxstudio.budgetbucket;

public class BudgetModel {
    private int expense;
    private String typeExpense,expenseData,expenseNote;

    BudgetModel(int expense,String typeExpense,String expenseData,String expenseNote){
        this.expense=expense;
        this.expenseData=expenseData;
        this.expenseNote=expenseNote;
        this.typeExpense=typeExpense;
    }


    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public String getTypeExpense() {
        return typeExpense;
    }

    public void setTypeExpense(String typeExpense) {
        this.typeExpense = typeExpense;
    }

    public String getExpenseData() {
        return expenseData;
    }

    public void setExpenseData(String expenseData) {
        this.expenseData = expenseData;
    }

    public String getExpenseNote() {
        return expenseNote;
    }

    public void setExpenseNote(String expenseNote) {
        this.expenseNote = expenseNote;
    }
}
