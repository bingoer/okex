package com.weber.okex.ticker.client.coinmarketcap.exception;


import com.weber.okex.ticker.client.coinmarketcap.domain.CmcApiError;

/**
 * An exception which can occur while invoking methods of the Binance API.
 */
public class CmcApiException extends RuntimeException {

    private static final long serialVersionUID = 3788669840036201041L;
/**
   * Error response object returned by Binance API.
   */
  private CmcApiError error;

  /**
   * Instantiates a new Okex api exception.
   *
   * @param error an error response object
   */
  public CmcApiException(CmcApiError error) {
    this.error = error;
  }

  /**
   * Instantiates a new Okex api exception.
   */
  public CmcApiException() {
    super();
  }

  /**
   * Instantiates a new Okex api exception.
   *
   * @param message the message
   */
  public CmcApiException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Okex api exception.
   *
   * @param cause the cause
   */
  public CmcApiException(Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new Okex api exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public CmcApiException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @return the response error object from Binance API, or null if no response object was returned (e.g. server returned 500).
   */
  public CmcApiError getError() {
    return error;
  }

  @Override
  public String getMessage() {
    if (error != null) {
      return error.getMsg();
    }
    return super.getMessage();
  }
}
