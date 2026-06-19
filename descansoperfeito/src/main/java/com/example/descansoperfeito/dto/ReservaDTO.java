package com.example.descansoperfeito.dto;

import java.time.LocalDate;

public record ReservaDTO(
        Long hospedeId,
        Long quartoId,
        LocalDate dataCheckin,
        LocalDate dataCheckout
) {}
