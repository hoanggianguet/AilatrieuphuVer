package Database;

/**
 * Created by Admin on 1/01/2016.
 */
public class QuestionItem {
    private int id;
    private String question,caseA, caseB, caseC, caseD;
    private int trueCase;

    public QuestionItem(int id, String question, String caseA, String caseB, String caseC, String caseD, int trueCase) {
        this.id = id;
        this.question = question;
        this.caseA = caseA;
        this.caseB = caseB;
        this.caseC = caseC;
        this.caseD = caseD;
        this.trueCase = trueCase;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCaseA() {
        return caseA;
    }

    public String getCaseB() {
        return caseB;
    }

    public String getCaseC() {
        return caseC;
    }

    public String getCaseD() {
        return caseD;
    }

    public int getTrueCase() {
        return trueCase;
    }

    @Override
    public String toString(){
        StringBuilder str=new StringBuilder();
        str.append("\nID: "+id)
                .append("\nQuestion: "+question)
                .append("\nCaseA: "+caseA)
                .append("\nCaseB: "+caseB)
                .append("\nCaseC: "+caseC)
                .append("\nCaseD: "+caseD)
                .append("\nTrue case: "+trueCase);

        return str.toString();
    }
}
