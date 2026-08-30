import { Link } from "react-router-dom"

function AlbumFolder(){

    return(

        <Link className="
        
            bg-neutral-200
            w-[200px]
            h-[200px]
            rounded-xl
            shadow-[0_0px_10px_rgba(0,0,0,0.25)]
            pl-2
            pt-2

            hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
            transition
            duration-100

            active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]

        ">

            <p className="
            
                text-2xl
            
            ">Title</p>
            <p>100 photos</p> 

        </Link>

    )

}

export default AlbumFolder