package co.com.jcga.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address", schema = "quarkus")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",
			updatable = false)
	private UUID id;

	@OneToOne
	private UserModel user;

	@Column(name = "street_1")
	private String street1;

	@Column(name = "street_2")
	private String street2;

	private String city;

	private String state;

	private String country;

	@Column(name = "postal_code")
	private String postalCode;

	private String phone;
}
