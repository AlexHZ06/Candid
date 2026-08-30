import TagsCatSelection from "./tagsCatSelection"
import { useState } from "react"

function SearchBar(){

    const [hidden, setHidden] = useState(true)

    function filterPress(){

        if(hidden){

            setHidden(false)

        }
        else{

            setHidden(true)

        }

    }

    return(

        <div className="
        
            flex
            gap-2
            justify-center
            items-center
            flex-col

        ">
        <div className="

            flex 
            gap-2
            mb-3

        ">
            <input placeholder="What are we looking for ?" type="text" className="
            
                border-2
                border-neutral-300
                rounded-4xl
                pl-2
                w-[30vh]
                h-[4vh]

                hover:border-neutral-400
                active:border-neutral-500
                hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                focus:ring-0
            
            "/>
            <button className="
            
                h-[4vh]
                w-[4vh]
                border-2
                border-neutral-300
                rounded-4xl
                p-2
                hover:border-neutral-400
                hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                active:border-black

            ">
                <img src="src/resources/Search--Streamline-Ultimate.png"/>
            </button>
            <button onClick={filterPress} className="
            
                h-[4vh]
                w-[4vh]
                border-2
                border-neutral-300
                rounded-4xl
                p-2
                hover:border-neutral-400
                hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                active:border-black

            ">
                <img src="src\resources\Filter-3--Streamline-Ultimate.png"/>
            </button>
            
        </div>
        <TagsCatSelection hide={hidden}/>
        </div>

    )

}

export default SearchBar