package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.model.StatusVeiculo;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class RegistroVeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ProprietarioRepository proprietarioRepository;

    @Transactional
    public Veiculo salvar(Veiculo veiculo) {

        if(veiculo.getId() != null) {
            throw new NegocioException("Veículo a ser cadastrado não deve possuir um id");
        }

        boolean placaEmUso = veiculoRepository.findByPlaca(veiculo.getPlaca())
                        .filter(v -> !v.equals(veiculo))
                                .isPresent();
        if(placaEmUso) {
            throw new NegocioException("Já existe um veículo cadastrado com essa placa");
        }

        Proprietario proprietario = proprietarioRepository.findById(veiculo.getProprietario().getId())
                        .orElseThrow(() -> new NegocioException("Proprietário não encontrado"));

        veiculo.setProprietario(proprietario);
        veiculo.setStatus(StatusVeiculo.REGULAR);
        veiculo.setDataCadastro(OffsetDateTime.now());

        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public void excluir(Long veiculoId) {
        veiculoRepository.deleteById(veiculoId);
    }
}
