package co.com.jcga.database.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user", schema = "quarkus", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserModel extends PanacheEntityBase {

	@Id
	@Column(name = "email")
	private String emailAddress;

	@Column(name = "merchant_id")
	private String merchantId;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "contact_phone")
	private String contactPhone;

	@Column(name = "dni_number")
	private String dniNumber;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private AddressModel address;

}
