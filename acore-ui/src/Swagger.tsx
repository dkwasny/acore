import SwaggerUI from "swagger-ui-react"
import "swagger-ui-react/swagger-ui.css"

const serverName = window.location.origin;
const openapiUrl = `${serverName}/openapi.yaml`;

function Swagger() {
  return (
    <SwaggerUI url={openapiUrl} />
  )
}

export default Swagger
