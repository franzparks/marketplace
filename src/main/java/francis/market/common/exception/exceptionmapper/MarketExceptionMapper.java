package francis.market.common.exception.exceptionmapper;

import francis.market.common.exception.MarketException;
import francis.market.common.model.MarketResponse;
import francis.market.common.model.ErrorVO;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Global Handler for exceptions thrown by application
 */
@Slf4j
@Provider
public class MarketExceptionMapper implements ExceptionMapper<MarketException> {
    public MarketExceptionMapper() {
        log.info("Exception mapper registered.");
    }

    @Override
    public Response toResponse(MarketException e) {
        log.error("Exception occurred, statusCode: {} & message: {}", e.getStatusCode(), e.getMessage());
        ErrorVO errorVO = ErrorVO.builder()
                .withCode(e.getStatusCode())
                .withMessage(e.getMessage()).build();
        return Response.status(e.getStatusCode()).entity(
                MarketResponse.builder().withStatusCode(e.getStatusCode()).withErrorVO(errorVO).build())
                .build();
    }
}
