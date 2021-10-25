package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
