package com.epam.esm.controller;

import com.epam.esm.dto.*;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.model.*;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.AccessValidator;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  private static final Logger LOGGER = Logger.getRootLogger();

  private UserService<User, UserDTO, Long> userService;
  private TagService<Tag, TagDTO, Long> tagService;
  private PurchaseService<Purchase, PurchaseDTO, Long> purchaseService;
  private GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> giftCertificateService;

  @Autowired
  public UserController(
      UserService<User, UserDTO, Long> userService,
      TagService<Tag, TagDTO, Long> tagService,
      PurchaseService<Purchase, PurchaseDTO, Long> purchaseService,
      GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> giftCertificateService) {
    this.userService = userService;
    this.tagService = tagService;
    this.purchaseService = purchaseService;
    this.giftCertificateService = giftCertificateService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("permitAll()")
  public void create(
      @Valid @RequestBody UserDTO userDTO,
      HttpServletResponse response,
      HttpServletRequest request) {

    LOGGER.debug("UserController : create for user: " + userDTO);

    Long id = userService.create(userDTO).getId();
    response.addHeader("Location", request.getRequestURL() + "/" + id);
  }

  @PutMapping(value = "/{id}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
  public UserDTO update(
      @PathVariable("id") Long id,
      @Valid @RequestBody UserDTO userDTO,
      HttpServletResponse response) {

    LOGGER.debug("UserController : updateEntity for id: " + id);
    AccessValidator.isAccessAllowed(id);
    userDTO.setId(id);
    userService.update(userDTO);
    return userService.getById(id);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('ADMIN')")
  public void delete(@PathVariable Long id) throws IncorrectParameterException {

    LOGGER.debug("UserController : delete for id: " + id);
    if (userService.getById(id).getRole().equals(RoleType.ADMIN)) {
      throw new IncorrectParameterException("You cannot delete admin.");
    }
    userService.delete(id);
  }

  @JsonView(View.Public.class)
  @GetMapping(value = "/{id}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
  public UserDTO getById(@PathVariable Long id) {

    LOGGER.debug("UserController : getById for id: " + id);
    AccessValidator.isAccessAllowed(id);
    return userService.getById(id);
  }

  @JsonView(View.Public.class)
  @GetMapping(value = "/login")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
  public UserDTO getByLogin(
          @RequestParam(value = "login", required = true) String login) {

    LOGGER.debug("UserController : getByLogin for login: " + login);

    UserDTO userDTO = userService.getByLogin(login);
    System.out.println(userDTO);
    AccessValidator.isAccessAllowed(userDTO.getId());
    return userDTO;
  }

  @JsonView(View.Public.class)
  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<UserDTO> getAll(
      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
      HttpServletResponse response) {

    LOGGER.debug("UserController : getAll : page: " + page + ", size: " + size);

    if (!PaginationValidator.isPageValid(page)) {
      page = Constant.PAGE;
    }

    if (!PaginationValidator.isSizeValid(size)) {
      size = Constant.DEFAULT_SIZE;
    }

    List<UserDTO> userDTOS = userService.getAll(page.intValue(), size.intValue());
    response.addHeader("X-Total-Count", userService.getEntityCount().toString());
    response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
    return userDTOS;
  }

  @JsonView(View.Public.class)
  @GetMapping(value = "/{id}/purchases")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
  public List<PurchaseDTO> getPurchaseByUserId(
      @PathVariable Long id,
      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
      HttpServletResponse response) {

    LOGGER.debug("UserController : getPurchaseByUserId : page: " + page + ", size: " + size);
    AccessValidator.isAccessAllowed(id);

    if (!PaginationValidator.isPageValid(page)) {
      page = Constant.PAGE;
    }

    if (!PaginationValidator.isSizeValid(size)) {
      size = Constant.DEFAULT_SIZE;
    }

    List<PurchaseDTO> purchaseDTOS =
        purchaseService.getPurchaseByUserId(id, page.intValue(), size.intValue());

    response.addHeader("X-Total-Count", purchaseService.getEntityCount().toString());
    response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
    return purchaseDTOS;
  }

  @PostMapping(value = "/{id}/purchases")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
  public void createPurchase(
      @RequestBody OrderDTO orderDTO,
      @PathVariable Long id,
      HttpServletResponse response,
      HttpServletRequest request) {

    LOGGER.debug("UserController : createPurchase");
    AccessValidator.isAccessAllowed(id);
    orderDTO.setUserId(id);
    Long idPurchase = purchaseService.createPurchase(orderDTO).getId();
    response.addHeader("Location", request.getRequestURL() + "/" + idPurchase);
  }

  @DeleteMapping(value = "/{id}/purchases/{purchaseId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('CLIENT')")
  public void cancelPurchase(@PathVariable Long id, @PathVariable Long purchaseId)
      throws IncorrectParameterException {

    LOGGER.debug("UserController : cancelPurchase");
    AccessValidator.isAccessAllowed(id);
    PurchaseDTO purchaseDTO = purchaseService.getById(purchaseId);

    if (purchaseDTO.getUserDTO().getId() == id) {
      purchaseService.delete(purchaseId);
    } else {
      throw new IncorrectParameterException("You don't have purchase with id: " + purchaseId);
    }
  }

  @GetMapping(value = "/{id}/purchases/tags")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
  public List<TagDTO> getPurchaseUserTags(
      @PathVariable Long id,
      @RequestParam(value = "sort", required = false) String sort,
      @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit) {

    LOGGER.debug("UserController : getPurchaseUserTags");

    AccessValidator.isAccessAllowed(id);

    List<TagDTO> tags = tagService.getPurchaseUserTags(id, sort, limit);
    return tags;
  }
}
