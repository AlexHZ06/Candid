import CHeader from "../compnents/cheader copy"
import SearchBar from "../compnents/searchBar"

function ImageSearch(){

    return(

        <div className="
        
            relative
            flex
            flex-col
            items-center
        
        ">
            <CHeader active="Search"/>
            <div className="
            
                
                absolute
                top-0
                mt-2.5
            
            ">
                <SearchBar />
            </div>
        </div>

    )
    
}

export default ImageSearch