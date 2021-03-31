package br.com.microservices.financeiro.services;

import br.com.microservices.financeiro.data.vo.VendaVO;
import br.com.microservices.financeiro.entity.ProdutoVenda;
import br.com.microservices.financeiro.entity.Venda;
import br.com.microservices.financeiro.exception.ResourceNotFoundException;
import br.com.microservices.financeiro.repository.ProdutoVendaRepository;
import br.com.microservices.financeiro.repository.VendaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoVendaRepository produtoVendaRepository;


    public VendaService(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoVendaRepository = produtoVendaRepository;
    }

    public VendaVO create(VendaVO vendaVO) {
        Venda venda = vendaRepository.save(Venda.create(vendaVO));

        var produtosSalvos = new ArrayList<ProdutoVenda>();
        vendaVO.getProdutos().forEach(p -> {
            ProdutoVenda pv = ProdutoVenda.create(p);
            pv.setVenda(venda);
            produtosSalvos.add(produtoVendaRepository.save(pv));
        });
        venda.setProdutos(produtosSalvos);

        return VendaVO.create(venda);
    }

    public Page<VendaVO> findAll(Pageable pageable) {
        var page = vendaRepository.findAll(pageable);
        return page.map(this::convertToVendaVO);
    }

    public VendaVO findById(Long id) {
        var entity = vendaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return VendaVO.create(entity);
    }

    private VendaVO convertToVendaVO(Venda venda) {
        return VendaVO.create(venda);
    }
}
