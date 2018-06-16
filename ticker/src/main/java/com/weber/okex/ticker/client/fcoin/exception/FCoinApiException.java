package com.weber.okex.ticker.client.fcoin.exception;


import com.weber.okex.ticker.client.fcoin.domain.FCoinApiError;

/**
 * An exception which can occur while invoking methods of the Binance API.
 */
public class FCoinApiException extends RuntimeException {

    private static final long serialVersionUID = 3788669840036201041L;
/**
   * Error response object returned by Binance API.
   */
  private FCoinApiError error;

  /**
   * Instantiates a new Okex api exception.
   *
   * @param error an error response object
   */
  public FCoinApiException(FCoinApiError error) {
    this.error = error;
  }

  /**
   * Instantiates a new Okex api exception.
   */
  public FCoinApiException() {
    super();
  }

  /**
   * Instantiates a new Okex api exception.
   *
   * @param message the message
   */
  public FCoinApiException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Okex api exception.
   *
   * @param cause the cause
   */
  public FCoinApiException(Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new Okex api exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public FCoinApiException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @return the response error object from Binance API, or null if no response object was returned (e.g. server returned 500).
   */
  public FCoinApiError getError() {
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
