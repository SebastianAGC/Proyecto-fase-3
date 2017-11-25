public class ErrorType {
    int errorNumber=0;
    int errorLine=0;

    public ErrorType(int errorNumber, int errorLine) {
        this.errorNumber = errorNumber;
        this.errorLine = errorLine;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public int getErrorLine() {
        return errorLine;
    }

    public void setErrorLine(int errorLine) {
        this.errorLine = errorLine;
    }
}
