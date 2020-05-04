package world.podo.emergency.domain.country;

import org.springframework.data.jpa.repository.JpaRepository;

interface ContactRepository extends JpaRepository<Contact, Long> {
}
