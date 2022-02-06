package Main;

import java.io.IOException;

public class CMD {
	public static void main(String[] args) {
		try {
		    Runtime.getRuntime().exec("ma commande" );
		} catch (IOException t) { }

	}
}
