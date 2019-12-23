package rest.data;

public class OpResponse {
	
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public OpResponse() {
		super();
	}

	public OpResponse(String result) {
		super();
		this.result = result;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.result;
	}

}
