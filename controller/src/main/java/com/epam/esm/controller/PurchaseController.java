package com.epam.esm.controller;

import com.epam.esm.dto.PurchaseDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Constant;
import com.epam.esm.util.PaginationValidator;
import com.epam.esm.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/purchases", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseController {

  private static final Logger LOGGER = Logger.getRootLogger();

  private PurchaseService<Purchase, PurchaseDTO, Long> purchaseService;
  private UserService<User, UserDTO, Long> userService;

  @Autowired
  public PurchaseController(
      PurchaseService<Purchase, PurchaseDTO, Long> purchaseService,
      UserService<User, UserDTO, Long> userService) {
    this.purchaseService = purchaseService;
    this.userService = userService;
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('ADMIN')")
  public void delete(@PathVariable Long id) {

    LOGGER.debug("PurchaseController : delete for id: " + id);
    purchaseService.delete(id);
  }

  @JsonView(View.Public.class)
  @GetMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public PurchaseDTO getById(@PathVariable Long id) {

    LOGGER.debug("PurchaseController : getById for id: " + id);
    return purchaseService.getById(id);
  }

  @JsonView(View.Public.class)
  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<PurchaseDTO> getAll(
      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
      HttpServletResponse response) {

    LOGGER.debug("PurchaseController : getAll : page: " + page + ", size: " + size);

    if (!PaginationValidator.isPageValid(page)) {
      page = Constant.PAGE;
    }

    if (!PaginationValidator.isSizeValid(size)) {
      size = Constant.DEFAULT_SIZE;
    }

    List<PurchaseDTO> purchaseDTOS = purchaseService.getAll(page.intValue(), size.intValue());
    response.addHeader("X-Total-Count", purchaseService.getEntityCount().toString());
    response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
    return purchaseDTOS;
  }
}
