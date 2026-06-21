package com.example.descansoperfeito.repository;

import com.example.descansoperfeito.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Page<Reserva> findAll(Pageable pageable);

    @Query("SELECT r FROM Reserva r WHERE r.quarto.id = :quartoId AND " +
            "r.dataCheckin < :checkout AND r.dataCheckout > :checkin")
    List<Reserva> findConflitos(@Param("quartoId") Long quartoId,
                                @Param("checkin") LocalDate checkin,
                                @Param("checkout") LocalDate checkout);
}
