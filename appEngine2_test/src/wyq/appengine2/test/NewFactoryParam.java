package wyq.appengine2.test;

import wyq.appengine2.factory.Factory.FactoryParameter;

public class NewFactoryParam implements FactoryParameter {

	private String v1;
	private int v2;

	public String getV1() {
		return v1;
	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	public int getV2() {
		return v2;
	}

	public void setV2(int v2) {
		this.v2 = v2;
	}

	public NewFactoryParam(String v1, int v2) {
		this.v1 = v1;
		this.v2 = v2;
	}


}
