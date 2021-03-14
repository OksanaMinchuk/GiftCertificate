package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Constant;
import com.epam.esm.util.PaginationValidator;
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
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

  private static final Logger LOGGER = Logger.getRootLogger();

  private GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> giftCertificateService;
  private UserService<User, UserDTO, Long> userService;

  @Autowired
  public GiftCertificateController(
      GiftCertificateService giftCertificateService, UserService<User, UserDTO, Long> userService) {
    this.giftCertificateService = giftCertificateService;
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('ADMIN')")
  public void create(
      @Valid @RequestBody GiftCertificateDTO giftCertificateDTO,
      HttpServletResponse response,
      HttpServletRequest request) {

    LOGGER.debug("GiftCertificateController : createGiftCertificateWithTag");
    Long id = giftCertificateService.create(giftCertificateDTO).getId();
    response.addHeader("Location", request.getRequestURL() + "/" + id);
  }

  @PutMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public GiftCertificateDTO update(
      @PathVariable("id") Long id, @Valid @RequestBody GiftCertificateDTO giftCertificateDTO) {

    LOGGER.debug("GiftCertificateController : updateEntity for id:" + id);
    giftCertificateDTO.setId(id);
    return giftCertificateService.updateEntity(giftCertificateDTO);
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public GiftCertificateDTO updateSingleField(
      @PathVariable("id") Long id, @RequestBody GiftCertificateDTO giftCertificateDTO) {

    LOGGER.debug("GiftCertificateController : updateSingleField for id:" + id);
    giftCertificateDTO.setId(id);
    return giftCertificateService.updateSingleField(giftCertificateDTO);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('ADMIN')")
  public void delete(@PathVariable Long id) {
    LOGGER.debug("GiftCertificateController : delete for id:" + id);
    giftCertificateService.delete(id);
  }

  @GetMapping(value = "/{id}")
  @PreAuthorize("permitAll()")
  public GiftCertificateDTO getGiftCertificateByID(@PathVariable Long id) {
    LOGGER.debug("GiftCertificateController : getGiftCertificateByID for id:" + id);
    return giftCertificateService.getById(id);
  }

  @GetMapping
  @PreAuthorize("permitAll()")
  public List<GiftCertificateDTO> getListByQuery(
      @RequestParam(value = "tagList", required = false) List<String> tagList,
      @RequestParam(value = "queryPart", required = false) String queryPart,
      @RequestParam(value = "sortBy", required = false) String sortBy,
      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
      HttpServletResponse response) {

    LOGGER.debug(
        "GiftCertificateController : getByQuery for tagList/queryPart/sort: "
            + tagList
            + "/"
            + queryPart
            + "/"
            + sortBy);
    LOGGER.debug("GiftCertificateController : getListByQuery : page: " + page + ", size: " + size);

    if (!PaginationValidator.isPageValid(page)) {
      page = Constant.PAGE;
    }

    if (!PaginationValidator.isSizeValid(size)) {
      size = Constant.DEFAULT_SIZE;
    }

    List<GiftCertificateDTO> giftCertificateDTOList =
        giftCertificateService.getListByQuery(
            tagList, queryPart, sortBy, page.intValue(), size.intValue());

    response.addHeader(
        "X-Total-Count",
        giftCertificateService.getCountGiftCertificates(tagList, queryPart, sortBy).toString());
    response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
    return giftCertificateDTOList;
  }
}
