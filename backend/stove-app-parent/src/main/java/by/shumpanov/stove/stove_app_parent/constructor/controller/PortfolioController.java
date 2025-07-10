package by.shumpanov.stove.stove_app_parent.constructor.controller;

import by.shumpanov.stove.stove_app_parent.constructor.dto.PortfolioItemDetailDto;
import by.shumpanov.stove.stove_app_parent.constructor.dto.PortfolioItemSummaryDto;
import by.shumpanov.stove.stove_app_parent.constructor.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<Page<PortfolioItemSummaryDto>> getPortfolio(
            @RequestParam(required = false) Long stoveTypeId,
            @PageableDefault(size = 9, sort = "completionDate") Pageable pageable) {

        Page<PortfolioItemSummaryDto> portfolioPage = portfolioService.getAllPortfolioItems(stoveTypeId, pageable);
        return ResponseEntity.ok(portfolioPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioItemDetailDto> getPortfolioItemById(@PathVariable Long id) {
        PortfolioItemDetailDto itemDetails = portfolioService.getPortfolioItemById(id);
        return ResponseEntity.ok(itemDetails);
    }

}
