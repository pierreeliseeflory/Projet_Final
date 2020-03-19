package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.model.Subscription;

class ModelTest {

	
//	public Membre(int id, String nom, String prenom, String adresse, String email, String telephone,
//			Subscription abonnement) {
//		
//	}
	@Test
	void createMember() {
		int id = 1;
		String name = "Flory";
		String firstName = "Pierre-Élisée";
		String adress = "14 rue Gavarnie";
		String mail = "pierre-elisee.flory@ensta-paris.fr";
		String phone = "0760934928";
		Subscription subscription = Subscription.valueOf("VIP");
		Membre myMember = new Membre(id, name, firstName, adress, mail, phone, subscription);
	}
	public static void main(String[] args){
	}
}
