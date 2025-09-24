package by.shumpanov.stove.stove_app_parent.constructor.service;

import by.shumpanov.stove.stove_app_parent.constructor.dto.PortfolioItemDetailDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.PortfolioItemSummaryDto;
import by.shumpanov.stove.stove_app_parent.constructor.model.Configuration;
import by.shumpanov.stove.stove_app_parent.constructor.model.PortfolioItem;
import by.shumpanov.stove.stove_app_parent.constructor.repository.ConfigurationRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.PortfolioItemRepository;
import by.shumpanov.stove.stove_app_parent.constructor.util.mapper.ConfigurationMapper;
import by.shumpanov.stove.stove_app_parent.security.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {
    private final PortfolioItemRepository portfolioItemRepository;
    private final ConfigurationRepository configurationRepository;
    private final ConfigurationMapper configurationMapper;
    private final ConfigurationService configurationService;

    @Transactional(readOnly = true)
    public Page<PortfolioItemSummaryDto> getAllPortfolioItems(Long stoveTypeId, Pageable pageable) {
        log.info("Fetching portfolio items. Filter by stoveTypeId: {}", stoveTypeId);

        Specification<PortfolioItem> specification = Specification.where(null);
        if (stoveTypeId != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("configuration").get("stoveType").get("id"), stoveTypeId)
            );
        }

        Page<PortfolioItem> page = portfolioItemRepository.findAll(specification, pageable);

        return page.map(this::mapToSummeryDto);
    }

    @Transactional(readOnly = true)
    public PortfolioItemDetailDto getPortfolioItemById(Long id) {
        log.info("Fetching portfolio item with id: {}", id);

        PortfolioItem portfolioItem = portfolioItemRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Portfolio item not found with id: " + id));

        Configuration configuration = configurationRepository.findByIdWithDetails(
                portfolioItem.getConfiguration().getId()).orElseThrow(() ->
                new IllegalStateException("Configuration for portfolio item not found"));
        return mapToDetailDto(portfolioItem, configuration);
    }


    private PortfolioItemSummaryDto mapToSummeryDto(PortfolioItem item) {
        return  PortfolioItemSummaryDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .mainImageUrl(item.getMainImageUrl())
                .completionDate(item.getCompletionDate())
                .stoveTypeName(item.getConfiguration().getStoveType().getName())
                .build();
    }

    private PortfolioItemDetailDto mapToDetailDto(PortfolioItem item, Configuration configuration) {
        BigDecimal totalPrice = configurationService.calculateTotalPrice(configuration);
        return PortfolioItemDetailDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .mainImageUrl(item.getMainImageUrl())
                .completionDate(item.getCompletionDate())
                .configuration(configurationMapper.toResponseDto(configuration, totalPrice))
                .build();
    }
}
