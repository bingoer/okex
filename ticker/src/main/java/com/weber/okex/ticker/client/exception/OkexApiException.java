package com.weber.okex.ticker.client.exception;


import com.weber.okex.ticker.client.domain.OkexApiError;

/**
 * An exception which can occur while invoking methods of the Binance API.
 */
public class OkexApiException extends RuntimeException {

    private static final long serialVersionUID = 3788669840036201041L;
/**
   * Error response object returned by Binance API.
   */
  private OkexApiError error;

  /**
   * Instantiates a new Okex api exception.
   *
   * @param error an error response object
   */
  public OkexApiException(OkexApiError error) {
    this.error = error;
  }

  /**
   * Instantiates a new Okex api exception.
   */
  public OkexApiException() {
    super();
  }

  /**
   * Instantiates a new Okex api exception.
   *
   * @param message the message
   */
  public OkexApiException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Okex api exception.
   *
   * @param cause the cause
   */
  public OkexApiException(Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new Okex api exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public OkexApiException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @return the response error object from Binance API, or null if no response object was returned (e.g. server returned 500).
   */
  public OkexApiError getError() {
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
